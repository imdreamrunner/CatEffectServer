auth = this.auth
managerList = {}

loadManagers = () ->
  table = _.template $("#manager-row").html()
  for manager in managerList
    $("#managers-tbody").append(table(manager))
  $('#manager-list').find('.content-loader').removeClass('content-loader');

ajaxLoadManagers =
  url:        "/system/managers/getAll"
  data:       {auth_username: auth['username'], auth_password: auth['password']}
  type:       "post"
  dataType:   "json"
  success:    (data) ->
    # console.log data
    if (!data['error'])
      managerList = data['managers']
      loadManagers()
  error:      () ->
    console.log "error"

$.ajax ajaxLoadManagers

findManager = (managerId) ->
  for manager in managerList
    if manager['managerId'] == managerId
      return manager

this.editManager = (managerId) ->
  this.showPopUp("#pop-up-manager", findManager(managerId), 500, 400)