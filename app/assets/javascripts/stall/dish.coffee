# Handler called when page is loaded
this.pageLoad ->
  this.getStallId(setStallId) # defined in stall/common.coffee

# The callback function of getStallId
setStallId = (stallId) ->
  this.stallId = stallId
  displayDish()

loadDish = (handler) ->
  this.dishId = dishId = this.params["dishId"]
  $.ajax
    url:      "/public/dishes/getOne/" + dishId
    type:     "get"
    dataType: "json"
    success:  (data) ->
      if (!data['error'])
        this.dish = dish = data['dish']
        handler(dish)

displayDish = ->
  dishDisplayTemplate = _.template $("#dish-display-template").html()
  displayDishHandler = (dish) ->
    $("#dish-display-table").html dishDisplayTemplate(dish)
    $("#dish-display").removeClass "hidden"
    $("#dish-edit").addClass "hidden"
  loadDish(displayDishHandler)

this.editDish = ->
  dishEditTemplate = _.template $("#dish-edit-template").html()
  editDishHandler = (dish) ->
    $("#dish-edit-form").html dishEditTemplate(dish)

    $("#startHour").val Math.floor(dish['promotionStart'] / 60)
    $("#startMinute").val dish['promotionStart'] % 60
    $("#endHour").val Math.floor(dish['promotionEnd'] / 60)
    $("#endMinute").val dish['promotionEnd'] % 60

    $("#dish-display").addClass "hidden"
    $("#dish-edit").removeClass "hidden"
  loadDish(editDishHandler)

this.saveDish = ->
  $("#inputPromotionStart").val parseInt($("#startHour").val()) * 60 + parseInt($("#startMinute").val())
  $("#inputPromotionEnd").val parseInt($("#endHour").val()) * 60 + parseInt($("#endMinute").val())
  that = this
  postData = $("#dish-edit-form").serialize()
  postData += "&auth_username=" + this.auth.getUsername();
  postData += "&auth_password=" + this.auth.getPassword();
  $.ajax
    url:      "/stall/dishes/edit/" + this.dishId
    data:     postData
    dataType: "json"
    type:     "post"
    success:  (data) ->
      if (!data['error'])
        if that.javaMode()
          that.java.refreshParent()
        displayDish()


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
        $("#dish-edit").find("#inputImage").val(data['image'])
        changeImg = ->
          $("#dish-edit").find("#imgImage").attr("src", "/assets/uploads/" + data['image'])
        setTimeout(changeImg, 1000)

this.deleteDish = ->
  this.showPopBox("#pop-up-confirm-delete", this.dish, 400, 180)

this.doDelete = ->
  that = this
  $.ajax
    url:      "/stall/dishes/delete/" + this.dishId
    data:
              auth_username: this.auth.getUsername()
              auth_password: this.auth.getPassword()
    dataType: "json"
    type:     "post"
    success:  (data) ->
      if (!data['error'])
        if that.javaMode()
          that.java.refreshParent()
          that.java.close()
