auth = this.auth
transactionList = []
canteenConstrainList = []

table = _.template $("#transaction-row").html()
canteenConstrainTable = _.template $("#canteenConstrain-row").html()

this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(4)
  console.log("start")
  ajaxLoadCanteenConstrain()
  ajaxLoadData()

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
        transactionList = data['transaction']
        loadTransaction()
    error:      () ->
      console.log "error"

ajaxLoadCanteenConstrain = ->
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

this.loadTransaction = loadTransaction = () ->
  $("#transaction-tbody").html ""
  for transaction in transactionList
    $("#transaction-tbody").append(table(transaction))
  $('#transaction-list').find('.content-loader').removeClass('content-loader');

this.loadCanteenConstrain = loadCanteenConstrain = () ->
  console.log("CanteenList:")
  console.log(canteenConstrainList)
  $("#constrain-canteenlist").html ""
  for canteenConstrain in canteenConstrainList
    $("#constrain-canteenlist").append(canteenConstrainTable(canteenConstrain))

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