
# Pop up

this.showPopUp = showPopUp = (template, data, width, height) ->
  if data == undefined
    data = {}
  tmpl = _.template $(template).html()
  popUpDiv = $('<div class="pop-up"></div>')
  popUpDiv.append tmpl(data)
  css =
    width:      width
    height:     height
    marginLeft: - width / 2
    marginTop:  - height / 2
  popUpDiv.css css
  $("body").append popUpDiv

