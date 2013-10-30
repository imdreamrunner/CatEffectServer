this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(6)

  loadCanteens()

canteenList = []

loadCanteens = ->
  $.ajax
    url:        "/public/canteens/getAll"
    type:       "get"
    dataType:   "json"
    success:    (data) ->
      if (!data['error'])
        canteenList = data['canteens']
        displayCanteens()
      else
        alert data['message']

displayCanteens = ->
  console.log canteenList
  template = _.template $("#canteen-row").html()
  for canteen in canteenList
    $("#canteens-tbody").append(template(canteen))
  $("#canteen-list").find(".content-loader").removeClass("content-loader")

this.newCanteen = ->
  this.showPopBox("#pop-up-add-canteen", {}, 400, 270)

this.doAddCanteen = ->
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
    name: $(".popbox .canteen-name").val()
  $.ajax
    url: "/system/canteens/add"
    type: "post"
    dataType: "json"
    data: postData
    success: (data) ->
      if (!data['error'])
        alert "Canteen added!"
        location.reload()
      else
        alert data['message']

findCanteen = (canteenId) ->
  for canteen in canteenList
    if canteen.canteenId == canteenId
      return canteen

this.editCanteen = (canteenId) ->
  this.showPopBox("#pop-up-edit-canteen", findCanteen(canteenId), 400, 270)

this.doEditCanteen = (canteenId) ->
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
    name: $(".popbox .canteen-name").val()
    sort: $(".popbox .canteen-sort").val()
  console.log postData
  $.ajax
    url: "/system/canteens/edit/" + canteenId
    type: "post"
    dataType: "json"
    data: postData
    success: (data) ->
      if (!data['error'])
        alert "Canteen edited!"
        location.reload()
      else
        alert data['message']

this.deleteCanteen = (canteenId) ->
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
  console.log postData
  $.ajax
    url: "/system/canteens/delete/" + canteenId
    type: "post"
    dataType: "json"
    data: postData
    success: (data) ->
      if (!data['error'])
        alert "Canteen deleted!"
        location.reload()
      else
        alert data['message']