auth = this.auth
transactionList = []
orderList = []
canteenConstrainList = []
stallConstrainList = []

table = _.template $("#transaction-row").html()
canteenConstrainTable = _.template $("#canteenConstrain-row").html()
stallConstrainTable = _.template $("#stallConstrain-row").html()
constrain = {canteen: -1, stall: -1}

this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(4)
  console.log("start")
  ajaxLoadData()
  ajaxLoadCanteenConstrain()
  ajaxLoadStallConstrain()

ajaxLoadData = ->
  $.ajax
    url:        "/system/transactions/getAll"
    type:       "post"
    dataType:   "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
    success:    (data) ->
      if (!data['error'])
        transactionList = data['transactions']
        loadTransaction()
    error:      () ->
      console.log "error"

ajaxLoadCanteenConstrain = ->
  $.ajax
    url:        "/public/canteens/getAll"
    type:       "get"
    dataType:   "json"
    success:    (data) ->
      if (!data['error'])
        console.log("LoadedCanteen")
        canteenConstrainList = data['canteens']
        loadCanteenConstrain()
    error:      () ->
      console.log "error"

ajaxLoadStallConstrain = ->
  $.ajax
    url:        "/public/stalls/getAll"
    type:       "get"
    dataType:   "json"
    success:    (data) ->
      if (!data['error'])
        stallConstrainList = data['stalls']
    error:      () ->
      console.log "error"

this.loadTransaction = loadTransaction = () ->
  $("#transaction-tbody").html ""
  for transaction in transactionList
    if (constrain.canteen != -1 && transaction['canteenId'] != constrain.canteen)
      continue
    if (constrain.stall != -1 && transaction['stallId'] != constrain.stall)
      continue
    $("#transaction-tbody").append(table(transaction))
  $('#transaction-list').find('.content-loader').removeClass('content-loader');

this.loadCanteenConstrain = loadCanteenConstrain = () ->
  for canteenConstrain in canteenConstrainList
    $("#constrain-canteenlist").append(canteenConstrainTable(canteenConstrain))

this.setCanteenConstrain = setCanteenConstrain = (canteenId,canteenName) ->
  #console.log("This is setCanteenConstrain (" + canteenId + ")")
  $("#selected-canteenconstrain").html canteenName + ' <span class="caret"></span>'
  constrain.canteen = canteenId
  constrain.stall = -1
  loadTransaction()
  setStallConstrainList(canteenId)

this.setStallConstrainList = setStallConstrainList = (canteenId) ->
  #console.log("This is setStallConstrainList (" + canteenId + ")")
  $("#selected-stallconstrain").html "ALL <span class='caret'></span>"
  $("#constrain-stalllist").html ""
  $("#constrain-stalllist").append(stallConstrainTable({stallId: -1, name: "ALL"}))
  if (canteenId != -1)
    for stallConstrain in stallConstrainList
      console.log(stallConstrain)
      if (stallConstrain['canteenId'] == canteenId)
        $("#constrain-stalllist").append(stallConstrainTable(stallConstrain))

this.setStallConstrain = setStallConstrain = (stallId,stallName) ->
  #console.log("This is setStallConstrain (" + stallId + ")")
  $("#selected-stallconstrain").html stallName+ ' <span class="caret"></span>'
  constrain.stall = stallId
  loadTransaction()

###
this.doAddPrepaidCard = ->
  that = this
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
  $.ajax
    url:      "/system/transaction/add"
    type:     "post"
    dataType: "json"
    data:     postData
    success:  (data) ->
      console.log data['message']
      ajaxLoadData()

this.deletePrepaidCard = (prepaidCardId)->
  PrepaidCard = getPrepaidCard(prepaidCardId)
  this.showPopBox("#pop-up-confirm-delete", PrepaidCard, 400, 180)

getPrepaidCard = (prepaidCardId) ->
  for PrepaidCard in prepaidCardList
    if PrepaidCard['prepaidCardId'] == prepaidCardId
      return PrepaidCard

this.confirmDeletePrepaidCard = (prepaidCardId) ->
  that = this
  $.ajax
    url:      "/system/prepaidCards/delete/" + prepaidCardId
    type:     "post"
    dataType: "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
    success: (data) ->
      console.log data
      if (!data['error'])
        ajaxLoadData()
        that.closePopBox()
    error: () ->
      console.log "error!"
###