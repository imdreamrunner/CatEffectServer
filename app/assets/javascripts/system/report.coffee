this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(5)
  ajaxLoadCanteens()

canteenList = []
stallList = []

ajaxLoadCanteens = ->
  that = this
  $.ajax
    url:        "/public/canteens/getAll"
    type:       "get"
    dataType:   "json"
    success:    (data) ->
      if (!data['error'])
        console.log("LoadedCanteen")
        canteenList = data['canteens']
        ajaxLoadStalls()
    error:      () ->
      console.log "error"

ajaxLoadStalls = ->
  $.ajax
    url:        "/public/stalls/getAll"
    type:       "get"
    dataType:   "json"
    success:    (data) ->
      if (!data['error'])
        stallList = data['stalls']
        loadCanteens()
    error:      () ->
      console.log "error"

loadCanteens = ->
  template = _.template $("#canteen-option-template").html()
  $("#canteen").html ""
  for canteen in canteenList
    $("#canteen").append(template(canteen))
  $("#canteen").on "change", ->
    loadStall parseInt($("#canteen").val())
  loadStall canteenList[0]['canteenId']

loadStall = (canteenId) ->
  template = _.template $("#stall-option-template").html()
  $("#stall").html ""
  for stall in stallList
    if stall['canteenId'] == canteenId
      $("#stall").append(template(stall))
  $("#stall").val($("#stall option:first").val());

this.generate = ->
  stallId = parseInt($("#stall").val())
  year = parseInt($("#year").val())
  month = parseInt($("#month").val())
  newWindow("/stall/report/page#stallId="+stallId+"&year="+year+"&month="+month, 700, 550)
