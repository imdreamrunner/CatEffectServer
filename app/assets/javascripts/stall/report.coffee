this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(4)
  this.getStallId(setStallId)

setStallId = (stallId) ->
  this.stallId = stallId

this.generate = ->
  stallId = this.stallId
  year = parseInt($("#year").val())
  month = parseInt($("#month").val())
  newWindow("/stall/report/page#stallId="+stallId+"&year="+year+"&month="+month, 700, 550)