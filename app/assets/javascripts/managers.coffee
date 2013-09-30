auth = this.auth
managerList = {}

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

this.editManager = (managerId) ->
  manager = findManager(managerId)
  this.showPopBox("#pop-up-manager", manager, 400, 270)

this.pageLoad ->
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
