auth = this.auth
this.managerList = managerList = {}

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

this.editManager = (managerId) ->
  this.showPopUp("#pop-up-manager", {}, 500, 400)