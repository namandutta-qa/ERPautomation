package com.erp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v143.network.Network;

/**
 * Helper to attach DevTools network listeners and inject action-correlation script.
 * Prints request/response payloads to stdout and stores logs for later retrieval.
 */
public class NetworkLogger {

    private final DevTools devTools;
    private final List<String> apiLogs = new ArrayList<>();

    public NetworkLogger(DevTools devTools) {
        this.devTools = devTools;
        try {
            this.devTools.createSession();
        } catch (Exception ignore) {
            // session may already exist
        }

        // enable network
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

        final Map<String, String> requestPayloads = new HashMap<>();
        final Map<String, String> requestActionIds = new HashMap<>();

        devTools.addListener(Network.requestWillBeSent(), request -> {
            try {
                String reqId = request.getRequestId().toString();
                String url = request.getRequest().getUrl();
                String httpMethod = request.getRequest().getMethod();
                String postData = "";
                if (request.getRequest().getPostData().isPresent()) {
                    postData = request.getRequest().getPostData().get();
                }

                String actionId = "";
                try {
                    Map<String, Object> headers = request.getRequest().getHeaders();
                    if (headers != null && headers.containsKey("X-Test-Action-Id")) {
                        actionId = String.valueOf(headers.get("X-Test-Action-Id"));
                    }
                } catch (Exception ignore) {
                }

                requestPayloads.put(reqId, postData);
                requestActionIds.put(reqId, actionId);

                if (postData == null || postData.isEmpty()) {
                    if (actionId == null || actionId.isEmpty()) {
                        System.out.println("[API REQUEST] " + httpMethod + " " + url + " (no payload)");
                    } else {
                        System.out.println("[API REQUEST] " + httpMethod + " " + url + " (no payload) [actionId=" + actionId + "]");
                    }
                } else {
                    if (actionId == null || actionId.isEmpty()) {
                        System.out.println("[API REQUEST] " + httpMethod + " " + url + " PAYLOAD: " + postData);
                    } else {
                        System.out.println("[API REQUEST] " + httpMethod + " " + url + " PAYLOAD: " + postData + " [actionId=" + actionId + "]");
                    }
                }
            } catch (Exception e) {
                System.err.println("[DevTools] Error in requestWillBeSent listener: " + e.getMessage());
            }
        });

        devTools.addListener(Network.responseReceived(), response -> {
            try {
                String url = response.getResponse().getUrl();
                int status = response.getResponse().getStatus();

                Network.GetResponseBodyResponse body = devTools.send(Network.getResponseBody(response.getRequestId()));
                String responseBody = body.getBody();

                String log = url + " | " + status + " | " + responseBody;
                apiLogs.add(log);

                String reqId = response.getRequestId().toString();
                String requestPayload = requestPayloads.getOrDefault(reqId, "");
                String actionId = requestActionIds.getOrDefault(reqId, "");

                if (status >= 400) {
                    if (actionId == null || actionId.isEmpty()) {
                        System.out.println("[API RESPONSE] " + url + " | Status: " + status);
                    } else {
                        System.out.println("[API RESPONSE] " + url + " | Status: " + status + " [actionId=" + actionId + "]");
                    }
                    if (!requestPayload.isEmpty()) {
                        System.out.println("  -> Request payload: " + requestPayload);
                    }
                    System.out.println("  -> Response body: " + responseBody);
                } else {
                    if (requestPayload.isEmpty()) {
                        if (actionId == null || actionId.isEmpty()) {
                            System.out.println("[API] " + url + " | Status: " + status + " (no payload)");
                        } else {
                            System.out.println("[API] " + url + " | Status: " + status + " (no payload) [actionId=" + actionId + "]");
                        }
                    } else {
                        if (actionId == null || actionId.isEmpty()) {
                            System.out.println("[API] " + url + " | Status: " + status + " | Payload: " + requestPayload);
                        } else {
                            System.out.println("[API] " + url + " | Status: " + status + " | Payload: " + requestPayload + " [actionId=" + actionId + "]");
                        }
                    }
                }

                // cleanup
                requestPayloads.remove(reqId);
                requestActionIds.remove(reqId);

            } catch (Exception e) {
                System.out.println("[DevTools] ⚠️ Failed to read response body: " + e.getMessage());
            }
        });
    }

    public List<String> getApiLogs() {
        return apiLogs;
    }

    /**
     * Helper to inject the action-correlation script into page via JavascriptExecutor
     * if possible. Caller must cast their driver to JavascriptExecutor and execute
     * the returned script.
     */
    public static String getActionCorrelationScript() {
        String script = "(function(){"
                + "if(window.__testActionTrackerInstalled) return;"
                + "window.__testActionTrackerInstalled=true;"
                + "window.__testActionIdCounter=0;"
                + "window.__nextActionId=function(){return Date.now()+'-'+(++window.__testActionIdCounter);};"
                + "window.__currentActionId='';"
                + "document.addEventListener('click', function(e){try{window.__currentActionId = window.__nextActionId();}catch(e){}}, true);"
                + "if(window.fetch){const _fetch = window.fetch.bind(window);window.fetch = function(input, init){try{var action = window.__currentActionId || ''; if(!init) init = {}; if(!init.headers) init.headers = {}; init.headers['X-Test-Action-Id']=action;}catch(e){}; return _fetch(input, init);} }"
                + "(function(){ var origOpen = XMLHttpRequest.prototype.open; var origSend = XMLHttpRequest.prototype.send; XMLHttpRequest.prototype.open = function(method, url, async, user, password){ this.__url = url; return origOpen.apply(this, arguments); }; XMLHttpRequest.prototype.send = function(body){ try{ var action = window.__currentActionId || ''; this.setRequestHeader && this.setRequestHeader('X-Test-Action-Id', action);}catch(e){} return origSend.apply(this, arguments); }; })();"
                + "})();";
        return script;
    }
}
