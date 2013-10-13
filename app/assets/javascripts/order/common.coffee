this.pageLoad ->
  if this.javaMode()
    java.setFullScreen(true)

this.getStallId = (handler) ->
  $.ajax
    url:      "/stall/auth"
    type:     "post"
    dataType: "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
    success: (data) ->
      if (!data['error'])
        handler(data['currentManager']['stallId'])