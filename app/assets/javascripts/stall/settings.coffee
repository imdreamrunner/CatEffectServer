MatricDicount = 1.0

discountTable = _.template("#matriccard-discount").html()

this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(5)

this.setMatricDiscount = setMatricDiscount = (newDiscount)->