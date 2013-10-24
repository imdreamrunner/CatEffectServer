auth = this.auth
accounts = []
currentCanteen = 0;

table = _.template $("#matriculationcard-row").html()

this.loadAccounts = loadAccounts = () ->
  $("#matriculationcards-tbody").html ""
  for account in accounts
    console.log account
    $("#matriculationcards-tbody").append(table(account))
  $('#matriculationcards-list').find('.content-loader').removeClass('content-loader');

this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(2)

  $.ajax
    url:        "/system/accounts/getAllMatriculation"
    type:       "post"
    dataType:   "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
    success:    (data) ->
      if (!data['error'])
        console.log data['accounts']
        accounts = data['accounts']
        loadAccounts()
    error:      () ->
      console.log "error"

this.newPrepaidCard = ->
  this.showPopBox("#pop-up-new-matriculationcard", {}, 400, 150)

this.doAddPrepaidCard = ->
  that = this
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
  $.ajax
    url:      "/system/matriculationCards/add"
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