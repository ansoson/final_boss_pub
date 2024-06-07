Features


Cutscene Scripting - Grethell
- 5 pts - Text box sequences
- Locking out controls, besides space to progress
- When to lock out? Not ticking on screen
- Chalkboard for Variables that would be useful

- Weapons
- Times fought
- Times lost vs. won
- Upgraded weapons ("oh that looks different")
- Emotions
- Little "jolt" to dialogue boxes

5 pts - Detailed Dialogue Tree Using Saving/Loading To Keep Process
- Dialogue Manager vs Dialogue Box Object
- Getting Things from Save/Load Chalkboard

Sound - Sylvie

5 pts - Real-time sound manipulation (reverb and echo)
- "Roar" reverbed on weakened
- "Reverb" or echo percentage

5 pts - Positional sound
- Objects have a "play sound" which corresponds to an event
- Function can be called
- Grab parent component position, relate to other position, triangulate
- Attack "lands to right of you", "lava bubbling" on edge

Graphics - Anthony

10 pts - Skeletal animations
- Arms are seperate from body, can attack and swap out with other weapons independent of body
- whole character can dash, have movement lock, and have the body parts stay in sync
Main Character
- Body
- Tail 
- Arm Sprite with Onhand, can swap out with 6 weapons and upgraded versions for each
- Arm Sprite with Offhand. can swap out with 6 weapons and upgraded versions for each

- Z-System for Bigger/Smaller Object, objects have draw orders based on their lowest point on the object
- Plus just a lot and a lot of sprite work/assets

Persistence - Jack

5 pts - Saving and loading item upgrades + coin values + dialogue trees
- Implement save/load
- Ensuring all variables are kept at appropriate scope
- Setting up the classes for coin counting, the actual weapon item

Others - Sylvie

10 pts - Complete, polished UI system
- "Health bar"
- Pause menu (has weapons and stats on weapons)
- Decent positioning for everything
- Dialogue UI
- Should be able to be customized for different games

5 pts - Upgrade System - Jack

- Classes for weapon items
- Game logic for upgrades, including different upgrade types
- Gated final upgrades with different effects
