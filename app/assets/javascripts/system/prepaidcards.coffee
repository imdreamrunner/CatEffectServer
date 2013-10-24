auth = this.auth
prepaidCardList = []
currentCanteen = 0;

table = _.template $("#prepaidcard-row").html()

this.loadPrepaidcards = loadPrepaidCards = () ->
  console.log(prepaidCardList)
  $("#prepaidcards-tbody").html ""
  for prepaidCard in prepaidCardList
    $("#prepaidcards-tbody").append(table(prepaidCard))
  $('#prepaidcards-tbody').find('.content-loader').removeClass('content-loader');

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