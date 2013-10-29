this.pageLoad ->
  month = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
  $("#month").html "" + month[parseInt(this.params['month']) - 1] + " " + this.params['year']
  $.ajax
    url: "/public/stalls/getOne/" + parseInt(this.params['stallId'])
    success: (data)->
      if (!data['error'])
        this.stall = stall = data['stall']
        $("#stall-name").html stall.name

  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
    stallId: parseInt(this.params['stallId'])
    year: parseInt(this.params['year'])
    month: parseInt(this.params['month'])
  console.log postData
  $.ajax
    url: "/stall/report/getData"
    data: postData
    type: "post"
    dataType: "json"
    success: (data) ->
      if (!data['error'])
        $("#revenue").html "$" + displayMoney(data['totalRevenue'])
        $("#prepaid").html data['prepaid'] + "%"
        $("#student").html data['student'] + "%"
        $("#faculty").html data['faculty'] + "%"
        $("#staff").html data['staff'] + "%"