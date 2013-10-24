auth = this.auth
canteenList = {}
stallsList = {}
currentCanteen = 0;

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

this.loadPrepaidcards = loadPrepaidCards = () ->
  $("#prepaidcards-tbody").html ""
  for prepaidcard in prepaidcardList
    $("#prepaidcards-tbody").append(table(prepaidcard))
  $('#prepaidcards-tbody').find('.content-loader').removeClass('content-loader');

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

ajaxLoadPrepaidcards =
  url:        "/public/prepaidCards/getAll"
  type:       "get"
  dataType:   "json"
  success:    (data) ->
    if (!data['error'])
      prepaidCardList = data['prepaidCards']
      dataToLoad--
      loadData()
  error:      () ->
    console.log "error"


this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(2)
  $.ajax ajaxLoadCanteens
  $.ajax ajaxLoadStalls

this.newPrepaidCard = ->
  this.showPopBox("#pop-up-new-prepaidcard", {}, 400, 150)

this.doAddPrepaidCard = ->
  that = this
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
  $.ajax
    url:      "/system/prepaidCards/add"
    type:     "post"
    dataType: "json"
    data:     postData
    success:  (data) ->
      console.log data['message']


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