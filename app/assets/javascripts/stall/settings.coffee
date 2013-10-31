this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(5)
  this.getStallId(setStallId)


setStallId = (stallId) ->
  this.stallId = stallId
  that = this
  $.ajax
    url: "/public/stalls/getOne/" + stallId
    type: "get"
    dataType: "json"
    success: (data) ->
      if (!data['error'])
        that.stall = data['stall']
        loadStallSettings()

loadStallSettings = ->
  stall = this.stall
  console.log stall
  $("#inputName").val stall['name']
  $("#inputImage").val stall['image']
  $("#inputPrepaidDiscount").val stall['prepaidDiscount']
  $("#inputStudentDiscount").val stall['studentDiscount']
  $("#inputFacultyDiscount").val stall['facultyDiscount']
  $("#inputStaffDiscount").val stall['staffDiscount']

this.save = ->
  postData =
    stallId: this.stallId
    name: $("#inputName").val()
    image: $("#inputImage").val()
    prepaidDiscount: $("#inputPrepaidDiscount").val()
    studentDiscount: $("#inputStudentDiscount").val()
    facultyDiscount: $("#inputFacultyDiscount").val()
    staffDiscount: $("#inputStaffDiscount").val()
  $.ajax
    url: "/stall/stalls/edit"
    type: "post"
    dataType: "json"
    data: postData
    success: (data) ->
      if (!data['error'])
        $(".stall-info-edit").hide()
        $(".stall-info-ajax").removeClass("hidden")
        reload = ->
          location.reload()
        setTimeout(reload, 1000)
      else
        alert data['message']

this.chooseImage = ->
  oMyForm = new FormData()
  oMyForm.append("image", $("#inputFile")[0].files[0])
  $.ajax
    url: "/public/upload/image"
    data: oMyForm
    cache: false,
    contentType: false,
    processData: false,
    type: 'POST'
    success: (data) ->
      if (!data['error'])
        $("#inputImage").val(data['image'])