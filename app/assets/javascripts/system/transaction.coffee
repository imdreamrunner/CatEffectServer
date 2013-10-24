auth = this.auth
transactionList = []

table = _.template $("#transaction-row").html()

ajaxLoadData = ->
  $.ajax
    url:        "/system/transaction/getAll"
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

this.loadTransaction = loadTransaction = () ->
  console.log(transactionList)
  $("#transaction-tbody").html ""
  for transaction in transactionList
    console.log "here"
    console.log transaction
    $("#transaction-tbody").append(table(transactionList))
  $('#transaction-list').find('.content-loader').removeClass('content-loader');



this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(4)
  console.log("start")
  ajaxLoadData()

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