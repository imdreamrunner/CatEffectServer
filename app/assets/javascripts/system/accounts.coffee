this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(3)

this.scan = ->
  getAccountInfo (account) ->
    newWindow("/system/account#accountId=" + this.account['accountId'], 1000, 600)


getAccountString = ->
  if this.javaMode()
    accountString = this.java.getAccountString()
  else
    accountString = "2 U1220822F"
  if accountString == null
    accountString = ""
  return accountString

getAccountInfo = (callback) ->
  that = this
  accountString = getAccountString()
  $.ajax
    url: "/order/getAccountByString"
    type: "post"
    dataType: "json"
    data:
      accountString: accountString
    success: (data) ->
      if data['error'] == 0
        that.account = data['account']
        if that.account && that.account.hasOwnProperty("accountId")
          callback.call(that)