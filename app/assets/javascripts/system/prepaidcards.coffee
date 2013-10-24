auth = this.auth
prepaidCardList = []
currentCanteen = 0;

table = _.template $("#prepaidcard-row").html()

this.loadPrepaidcards = loadPrepaidCards = () ->
  console.log(prepaidCardList)
  $("#prepaidcards-tbody").html ""
  for prepaidCard in prepaidCardList
    console.log prepaidCard
    $("#prepaidcards-tbody").append(table(prepaidCard))
  $('#prepardcard-list').find('.content-loader').removeClass('content-loader');



this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(2)

  $.ajax
    url:        "/system/prepaidCards/getAll"
    type:       "post"
    dataType:   "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
    success:    (data) ->
      if (!data['error'])
        prepaidCardList = data['prepaidCards']
        loadPrepaidcards()
    error:      () ->
      console.log "error"


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
      location.reload()

this.deletePrepaidCard = (prepaidCardId)->
  PrepaidCard = getPrepaidCard(prepaidCardId)
  this.showPopBox("#pop-up-confirm-delete", PrepaidCard, 400, 180)

getPrepaidCard = (prepaidCardId) ->
  for PrepaidCard in prepaidCardList
    if PrepaidCard['prepaidCardId'] == prepaidCardId
      return PrepaidCard

this.confirmDeletePrepaidCard = (prepaidCardId) ->
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
        location.reload()
    error: () ->
      console.log "error!"