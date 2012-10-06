local c = {}

c["MF"] = turtle.forward
c["MB"] = turtle.back
c["MU"] = turtle.up
c["MD"] = turtle.down
c["ML"] = turtle.turnLeft
c["MR"] = turtle.turnRight

c["PF"] = turtle.place
c["PU"] = turtle.placeUp
c["PD"] = turtle.placeDown

c["DF"] = turtle.dig
c["DU"] = turtle.digUp
c["DD"] = turtle.digDown

c["CF"] = turtle.compare
c["CU"] = turtle.compareUp
c["CD"] = turtle.compareDown

c["LF"] = turtle.drop
c["LU"] = turtle.dropUp
c["LD"] = turtle.dropDown

c["AF"] = turtle.attack
c["AU"] = turtle.attackUp
c["AD"] = turtle.attackDown



function sensors()
  return "DD=" .. tostring(turtle.detectDown()) ..
  "&DF=" .. tostring(turtle.detect()) ..
  "&DU=" .. tostring(turtle.detectUp())
end
  
local last = true
local com
while true do
  local f = http.get("http://l.ikno.ws:3000/resp?" .. sensors() .. 
  	    	     "&SU=" .. tostring(last))
  com = f.readAll()
  f.close()
  print(com)
  if com == "EX" then break end
  last = c[com]()
end

