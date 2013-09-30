auth = this.auth = {}

pageLoadHandlers = []

this.pageLoad = pageLoad = (handler)->
  alert "add handler"
  pageLoadHandlers.push(handler)

this.javaPageLoad = javaPageLoad = ->
  alert(pageLoadHandlers.length)
  while pageLoadHandlers.length > 0
    pageLoadHandlers.shift().call(this)

$(window).load ->
  if !javaMode()
    javaPageLoad()

# Auth Data
auth.getUsername = ->
  console.log "get username"
  if javaMode()
    return java.getUsername()
  else
    return "test"
auth.getPassword = ->
  if javaMode()
    return java.getPassword()
  else
    return "test123"

# Pop up

this.showPopBox = showPopBox = (template, data, width, height) ->
  if data == undefined
    data = {}
  tmpl = _.template $(template).html()
  popBoxDiv = $('<div class="popbox"></div>')
  popBoxDiv.append tmpl(data)
  css =
    width:      width
    height:     height
    marginLeft: - width / 2
    marginTop:  - height / 2
    display:    "none"
  popBoxDiv.css css
  $("body").append popBoxDiv
  popBoxDiv.show(400)

this.closePopBox = closePopBox = () ->
  $('.popbox')
    .hide(400, () -> $(this).remove())


# Util functions

_javamode = "undetected"

this.javaMode = javaMode = ->
  if _javamode == "undetected"
    _javamode = document.URL.split("#")[1] != "browser"
    if _javamode
      alert "enter java mode"
    else
      console.log "enter browser mode"
  return _javamode