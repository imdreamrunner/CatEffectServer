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