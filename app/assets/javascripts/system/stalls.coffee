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

getStall = (stallId) ->
  for stall in stallList
    if stallId == stall['stallId']
      return stall

this.loadStalls = loadStalls = (canteenId) ->
  $('.canteen-button').removeClass "current"
  $('#canteen-button-' + canteenId).addClass "current"
  $('#canteen-name').html getCanteen(canteenId)['name']
  $("#stalls-tbody").html ""
  for stall in stallList
    if stall['canteenId'] == canteenId
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


this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(2)
  $.ajax ajaxLoadCanteens
  $.ajax ajaxLoadStalls

this.deleteStall = (stallId) ->
  stall = getStall(stallId)
  this.showPopBox("#pop-up-confirm-delete", stall, 400, 180)

this.confirmDeleteStall = (stallId) ->
  ajaxDeleteStall =
    url:      "/system/stalls/delete/" + stallId
    type:     "post"
    dataType: "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
    success: (data) ->
      console.log data
      if (!data['error'])
        location.reload()
    error: () ->
      console.log "error!"
  $.ajax ajaxDeleteStall