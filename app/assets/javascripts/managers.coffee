auth = this.auth
managerList = {}

# Add managerList to Table
loadManagers = () ->
  table = _.template $("#manager-row").html()
  for manager in managerList
    $("#managers-tbody").append(table(manager))
  $('#manager-list').find('.content-loader').removeClass('content-loader');

ajaxLoadManagers =
  url:        "/system/managers/getAll"
  data:
              auth_username: auth['username']
              auth_password: auth['password']
              type:          1
  type:       "post"
  dataType:   "json"
  success:    (data) ->
    if (!data['error'])
      managerList = data['managers']
      loadManagers()
  error:      () ->
    console.log "error"

findManager = (managerId) ->
  for manager in managerList
    if manager['managerId'] == managerId
      return manager

this.editManager = (managerId) ->
  manager = findManager(managerId)
  this.showPopUp("#pop-up-manager", manager, 400, 270)

$(document).ready () ->
  $.ajax ajaxLoadManagers
