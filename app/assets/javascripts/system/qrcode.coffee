this.pageLoad ->
  console.log "here"
  title = this.params['title']
  string = this.params['string']
  $img = $("<img src='http://www.sparqcode.com/qrgen?qt=raw&data=" + string + "&cap= " + title + "&col=333333' />")
  $img.on "load", ->
    $(".content").removeClass("content-loader")
  $("#qr-container").append($img)