managerList = {}

this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(5)
  this.getStallId (stallId) ->
    this.stallId = stallId
    ajaxLoadManagers =
      url:        "/stall/managers/getAll/"+ stallId
      data:
        auth_username: auth.getUsername()
        auth_password: auth.getPassword()
        type:          1
      type:       "post"
      dataType:   "json"
      success:    (data) ->
        if (!data['error'])
          managerList = data['managers']
          loadManagers()
      error:      () ->
        console.log "error"
    $.ajax ajaxLoadManagers

# Add managerList to Table
loadManagers = () ->
  table = _.template $("#manager-row").html()
  for manager in managerList
    $("#managers-tbody").append(table(manager))
  $('#manager-list').find('.content-loader').removeClass('content-loader');

findManager = (managerId) ->
  for manager in managerList
    if manager['managerId'] == managerId
      return manager

this.addManager = ->
  this.showPopBox("#pop-up-add-manager", {}, 400, 270)

this.editSelf = false

this.editManager = (managerId) ->
  manager = findManager(managerId)
  this.editSelf =  manager.username == this.auth.getUsername()
  this.showPopBox("#pop-up-manager", manager, 400, 270)

this.deleteManager = (managerId) ->
  manager = findManager(managerId)
  if manager.username == this.auth.getUsername()
    alert "You can't do that to yourself, can you?"
  else
    this.showPopBox("#pop-up-confirm-delete-manager", manager, 400, 200)

this.doAddManager = ->
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
    username: $(".popbox").find("#inputUsername").val()
    password: $(".popbox").find("#inputPassword").val()
    stallId:  this.stallId
  $.ajax
    url: "/system/managers/add"
    type: "post"
    dataType: "json"
    data: postData
    success: (data) ->
      if (!data['error'])
        alert "Manager added!"
        location.reload()
      else
        alert data['message']

this.doEditManager = (managerId) ->
  that = this
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
    username: $(".popbox").find("#inputUsername").val()
    password: $(".popbox").find("#inputPassword").val()
  $.ajax
    url: "/system/managers/edit/" + managerId
    type: "post"
    dataType: "json"
    data: postData
    success: (data) ->
      if (!data['error'])
        if that.editSelf
          alert "You have just edit yourself. Please login with your new information."
          if that.javaMode()
            that.java.exit()
        else
          alert "Changes saved!"
          location.reload()
      else
        alert data['message']


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
        alert "Manager deleted"
        location.reload()
      else
        alert data["message"]