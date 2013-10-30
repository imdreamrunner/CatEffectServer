auth = this.auth
managerList = {}

this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(6)

  ajaxLoadManagers =
    url:        "/system/managers/getAll"
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

this.editManager = (managerId) ->
  manager = findManager(managerId)
  this.showPopBox("#pop-up-manager", manager, 400, 270)

this.doAddManager = ->
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
    username: $(".popbox").find("#inputUsername").val()
    password: $(".popbox").find("#inputPassword").val()
    type: 1
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
        alert "Changes saved!"
        location.reload()
      else
        alert data['message']