auth = this.auth

loadManagers = (managerList) ->
  table = _.template $("#manager-row").html()
  for manager in managerList
    $("#managers-tbody").append(table(manager))

ajaxLoadManagers =
  url:        "/system/managers/getAll"
  data:       {auth_username: auth['username'], auth_password: auth['password']}
  type:       "post"
  dataType:   "json"
  success:    (data) ->
    # console.log data
    if (!data['error'])
      loadManagers data['managers']
  error:      () ->
    console.log "error"

$.ajax ajaxLoadManagers