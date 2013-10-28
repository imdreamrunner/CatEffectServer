this.pageLoad ->
  this.accountId = accountId = this.params['accountId']
  loadAccount()

loadTransaction = ->
  transactionTemplate = _.template $("#transaction-row").html()
  that = this
  $.ajax
    url:        "/system/transactions/getByAccount/" + accountId
    type:       "post"
    dataType:   "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
    success: (data) ->
      if (!data['error'])
        that.transactions = transactions = data['transactions']
        for transaction in transactions
          $("#transactions-tbody").append(transactionTemplate(transaction))
        $("#transaction-list").find(".content-loader").removeClass("content-loader")
    error: ->
      console.log "errer"

loadAccount = ->
  that = this
  $.ajax
    url:        "/system/accounts/getOne/" + accountId
    type:       "post"
    dataType:   "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
    success: (data) ->
      if (!data['error'])
        that.account = account = data['account']
        $("#tAccountId").html account['accountId']
        $("#tBalance").html account['balance']
        loadTransaction()
    error: ->
      console.log "errer"

this.amount = topUpAmount = 50

this.setAmount = (amount) ->
  topUpAmount = amount
  $(".top-up-amount a").removeClass("btn-primary").addClass("btn-default")
  $("#amount-" + amount).addClass("btn-primary")

this.doTopUp = ->
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
    amount:        topUpAmount * 100
    accountId:     this.accountId
  $.ajax
    url: "/system/topup"
    type: "post"
    dataType: "json"
    data: postData
    success: (data) ->
      if (!data["error"])
        $("#result").removeClass("hidden")
        $("#top-up-button").hide()
        reload = ->
          location.reload()
        setTimeout(reload, 1000)