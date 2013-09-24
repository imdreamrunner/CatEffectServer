auth = this.auth
stallList = {}
managerList = {}

loadManagers = () ->
  table = _.template $("#manager-row").html()
  for manager in managerList
    $("#managers-tbody").append(table(manager))
  $('#manager-list').find('.content-loader').removeClass('content-loader');

ajaxLoadStalls =
  url:        "/public/stalls/getAll"
  type:       "get"
  dataType:   "json"
  success:    (data) ->
    # console.log data
    if (!data['error'])
      stallList = data['stalls']
      $.ajax ajaxLoadManagers
  error:      () ->
    console.log "error"

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

findManager = (managerId) ->
  for manager in managerList
    if manager['managerId'] == managerId
      return manager

this.editManager = (managerId) ->
  manager = findManager(managerId)
  manager.stallList = stallList
  this.showPopUp("#pop-up-manager", manager, 400, 400)

this.changeManagerType = () ->
  $stallList = $(".pop-up").find(".stall-list")
  if parseInt($(".pop-up").find("#inputType").val()) == 1
    $stallList.addClass "hidden"
  else
    $stallList.removeClass "hidden"

$(document).ready () ->
  $.ajax ajaxLoadStalls
