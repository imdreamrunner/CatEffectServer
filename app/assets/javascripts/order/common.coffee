this.pageLoad ->
  if this.javaMode()
    java.setFullScreen(true)

this.getStall = (handler) ->
  if this.params["stallId"] == undefined
    alert "stallId is not defined!"
  $.ajax
    url:      "/public/stalls/getOne/" + this.params["stallId"]
    dataType: "json"
    success: (data) ->
      if (!data['error'])
        handler(data['stall'])