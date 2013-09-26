auth = this.auth
canteenList = {}
stallList = {}

dataToLoad = 2

table = _.template $("#stall-row").html()
canteenButton = _.template $("#canteen-button").html()

loadData = ->
  if dataToLoad != 0
    return
  $('#canteen-list').html ''
  for canteen in canteenList
    $('#canteen-list').append canteenButton(canteen)
  loadStalls(canteenList[0]['canteenId'])

getCanteen = (canteenId) ->
  for canteen in canteenList
    if canteenId == canteen['canteenId']
      return canteen

this.loadStalls = loadStalls = (canteenId) ->
  $('.canteen-button').removeClass "current"
  $('#canteen-button-' + canteenId).addClass "current"
  $('#canteen-name').html getCanteen(canteenId)['name']
  $("#stalls-tbody").html ""
  for stall in stallList
    if stall['canteen']['canteenId'] == canteenId
      $("#stalls-tbody").append(table(stall))
  $('#stall-list').find('.content-loader').removeClass('content-loader');

ajaxLoadCanteens =
  url:        "/public/canteens/getAll"
  type:       "get"
  dataType:   "json"
  success:    (data) ->
    if (!data['error'])
      canteenList = data['canteens']
      dataToLoad--
      loadData()
  error:      () ->
    console.log "error"

ajaxLoadStalls =
  url:        "/public/stalls/getAll"
  type:       "get"
  dataType:   "json"
  success:    (data) ->
    if (!data['error'])
      stallList = data['stalls']
      dataToLoad--
      loadData()
  error:      () ->
    console.log "error"


$(document).ready () ->
  $.ajax ajaxLoadCanteens
  $.ajax ajaxLoadStalls
