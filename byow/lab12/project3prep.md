# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer: The size of the world was different than mine and the position in the world was different as well.

-----

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer: Each hexagon placement/size could be replaced by a random room or hallway. The tesselation would be the whole world.

-----
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer: I would write helper methods to create different sizes of rooms and hallways. Then, I would write methods to add a room or hallway to the world.

-----
**What distinguishes a hallway from a room? How are they similar?**

Answer: Hallways should have a width of 1 or 2 tiles and a random length while the width and height of rooms sould be random. Rooms and hallways must have walls that are visually distinct from floors. Walls and floors should be visually distinct from unused spaces. Rooms and hallways should be connected, i.e. there should not be gaps in the floor between adjacent rooms or hallways.
