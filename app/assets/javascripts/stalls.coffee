auth = this.auth
canteenList = {}
stallList = {}

loadCanteens = ->
  console.log canteenList

loadManagers = ->
  table = _.template $("#stall-row").html()
  for manager in stallList
    $("#stalls-tbody").append(table(manager))
  $('#stall-list').find('.content-loader').removeClass('content-loader');

ajaxLoadCanteens =
  url:        "/public/canteens/getAll"
  type:       "get"
  dataType:   "json"
  success:    (data) ->
    if (!data['error'])
      canteenList = data['canteens']
      loadCanteens()
  error:      () ->
    console.log "error"

ajaxLoadStalls =
  url:        "/public/stalls/getAll"
  type:       "get"
  dataType:   "json"
  success:    (data) ->
    if (!data['error'])
      stallList = data['stalls']
      loadManagers()
  error:      () ->
    console.log "error"


$(document).ready () ->
  $.ajax ajaxLoadCanteens
  $.ajax ajaxLoadStalls
