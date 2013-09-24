auth = this.auth
stallList = {}

ajaxLoadStalls =
  url:        "/public/stalls/getAll"
  type:       "get"
  dataType:   "json"
  success:    (data) ->
    if (!data['error'])
      stallList = data['stalls']
      $.ajax ajaxLoadManagers
  error:      () ->
    console.log "error"