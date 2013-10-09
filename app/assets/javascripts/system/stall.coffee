managerList = []

# Add managerList to Table
loadManagers = () ->
  table = _.template $("#manager-row").html()
  for manager in managerList
    $("#managers-tbody").append(table(manager))
  $('#manager-list').find('.content-loader').removeClass('content-loader');

this.loadStall = ->
  $("#stall-info").addClass "hidden"
  $("#stall-display").removeClass "hidden"
  stallTableTemplate = _.template $("#stall-table-template").html()
  $.ajax
    url:        "/public/stalls/getOne/" + stallId
    type:       "get"
    dataType:   "json"
    success: (data) ->
      if (!data['error'])
        stall = data['stall']
        $stallDisplay = $("#stall-display")
        $stallDisplay.find(".ajax-loader").removeClass "ajax-loader"
        $stallDisplay.find(".stall-table").html stallTableTemplate(stall)
    error: ->
      console.log "errer"

this.editStall = ->
  stallInfoTemplate = _.template $("#stall-info-template").html()

  $.ajax
    url:        "/public/stalls/getOne/" + stallId
    type:       "get"
    dataType:   "json"
    success: (data) ->
      if (!data['error'])
        stall = data['stall']
        $("#stall-display").addClass "hidden"
        $stallInfo = $("#stall-info")
        $stallInfo.removeClass "hidden"
        $stallInfo.find(".stall-info-ajax").addClass "hidden"
        $stallInfo.find(".stall-info-save").show()
        $stallInfo.find(".ajax-loader").removeClass "ajax-loader"
        $stallInfo.find(".stall-info").html stallInfoTemplate(stall)
    error: ->
      console.log "errer"

this.pageLoad ->
  this.stallId = stallId = this.params['stallId']

  loadStall()

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
  $stallInfo = $("#stall-info");
  data = $stallInfo.find(".stall-info").serialize()
  data += "&auth_username=" + this.auth.getUsername();
  data += "&auth_password=" + this.auth.getPassword();
  $stallInfo.find(".stall-info-save").hide()
  $stallInfo.find(".stall-info-ajax").html "Saving..."
  $stallInfo.find(".stall-info-ajax").removeClass "hidden"
  $.ajax
    url:        "/system/stalls/edit/" + this.stallId
    type:       "post"
    data:       data
    dataType:   "json"
    success: (data) ->
      if (!data["error"])
        $stallInfo.find(".stall-info-ajax").html "Success!"
        # $stallInfo.find(".stall-info-save").show()
        loadStall()
        if that.javaMode()
          that.java.refreshParent()
          # that.java.close()
      else
        $stallInfo.find(".stall-info-ajax").html data['message']


findManager = (managerId) ->
  for manager in managerList
    if manager['managerId'] == managerId
      return manager

this.editManager = (managerId) ->
  manager = findManager(managerId)
  this.showPopBox("#pop-up-manager", manager, 400, 270)

this.deleteManager = (managerId) ->
  manager = findManager(managerId)
  this.showPopBox("#pop-up-confirm-delete-manager", manager, 500, 180)

this.newManager = () ->
  this.showPopBox("#pop-up-new-manager", {}, 400, 270)

this.doAddManager = ->
  username = $(".popbox .username").val()
  password = $(".popbox .password").val()
  postData =
    username: username
    password: password
    stallId:  this.stallId
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
  $.ajax
    url:      "/system/managers/add"
    type:     "post"
    dataType: "json"
    data:     postData
    success:  (data) ->
      if (!data["error"])
        location.reload()
      else
        alert data["message"]

this.confirmDeleteManager = (managerId) ->
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
  $.ajax
    url:      "/system/managers/delete/" + managerId
    type:     "post"
    dataType: "json"
    data:     postData
    success:  (data) ->
      if (!data["error"])
        location.reload()
      else
        alert data["message"]
