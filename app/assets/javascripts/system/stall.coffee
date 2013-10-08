managerList = []

# Add managerList to Table
loadManagers = () ->
  table = _.template $("#manager-row").html()
  for manager in managerList
    $("#managers-tbody").append(table(manager))
  $('#manager-list').find('.content-loader').removeClass('content-loader');


this.pageLoad ->
  this.stallId = stallId = this.params['stallId']
  stallInfoTemplate = _.template $("#stall-info-template").html()

  $.ajax
    url:        "/public/stalls/getOne/" + stallId
    type:       "get"
    dataType:   "json"
    success: (data) ->
      if (!data['error'])
        stall = data['stall']
        $("#stall-info-div").removeClass "ajax-loader"
        $("#stall-info").html stallInfoTemplate(stall)
    error: ->
      console.log "errer"

  $.ajax
    url:        "/system/managers/getAll"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
      stallId:       this.stallId
    type:       "post"
    dataType:   "json"
    success:    (data) ->
      if (!data['error'])
        managerList = data['managers']
        loadManagers()
      else
        console.log "error!!!"
    error:      () ->
      console.log "error"


this.saveStall = ->
  that = this
  data = $("#stall-info").serialize()
  data += "&auth_username=" + this.auth.getUsername();
  data += "&auth_password=" + this.auth.getPassword();
  $("#stall-info-save").hide()
  $("#stall-info-ajax").removeClass "hidden"
  $.ajax
    url:        "/system/stalls/edit/" + this.stallId
    type:       "post"
    data:       data
    dataType:   "json"
    success: (data) ->
      if (!data["error"])
        $("#stall-info-ajax").html "Success!"
        if that.javaMode()
          that.java.close(true)
      else
        $("#stall-info-ajax").html data['message']
