auth = this.auth = {}

pageLoadHandlers = []

this.pageLoad = pageLoad = (handler)->
  pageLoadHandlers.push(handler)

this.javaPageLoad = javaPageLoad = ->
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
  closePopBox();
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


# URL Router

this.params = params = {}

$(this).ready ->
  query = document.URL.split("#")[1]
  data = query.split('&')
  for item in data
    params[item.split('=')[0]] = item.split('=')[1]

_javamode = "undetected"

this.javaMode = javaMode = ->
  if _javamode == "undetected"
    _javamode = params['browser'] != "true"
    if _javamode
      alert "enter java mode"
    else
      console.log "enter browser mode"
  return _javamode