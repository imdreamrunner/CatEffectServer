this.setMenu = (id) ->
  $("li a.active").removeClass("active")
  $("ul#menu li:nth-child(" + id + ") a").addClass("active")