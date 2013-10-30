this.pageLoad ->
  that = this
  $.ajax
    url:      "/system/auth"
    type:     "post"
    dataType: "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
    success: (data) ->
      if (!data['error'])
        # Nothing to do
      else
        alert "Cannot verify your account. Please log in again."
        if that.javaMode()
          that.java.exit()