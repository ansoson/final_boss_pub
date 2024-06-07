ROADMAP


Nov 14-Nov 28

NOV 17
- familiarize with new engine, find points where I can feed info into a sprite system (movement, damage, attack), add diagonal movement
- Review and update engine text box implementation, create text data type
- Figure out all variables that need to be "tracked", create base Components for the weapons along with variables that can be manipulated, figure out what each "battle effect" would entail, set up chalkboard infrastructure for moving variables around.

NOV 21
- Establish a sprite hierarchy system base where one object has multiple sprites layered
- Create dialogue controller and dialogue tree structure, locking out controls, get variables from save files
- Have save-and-loadable upgrades that can be bought with money, have save occur as proper time.

NOV 28
- Finish hierarchy system, and using placeholder sprites, feed the proper inputs into all sprites (not perfect yet, leave another week for debugging
- Finalize dialogue tree, text wrapping, and text box UI elements
- Have attacks basically functional, as well as the base game systems for moving between scenes triggering after beating/losing the boss, essentially ensure that "core gameplay loop" exists and works as planned– and persists after loading.

---------------------------------------------------------------------------------

Feature Ideas


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
- Not quite but semi-scope creep:
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
- Arms Move Separately of Body
- Implement Get Center
- Z-System for Bigger/Smaller Object
- I-k system (potentially)

Main Character
- Body
- Arm Sprite with Onhand
- Arm Sprite with Offhand

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
- Game logic for upgrades
- "Beating the game" in certain ways lets you do certain final upgrades
- Mutually exclusive final upgrades

ADDITIONAL INFO

Implemented by the 28th - boss attacks
- two hands that have a slam attack
- fireball projectile attack
- laser aoe attack

Weapon plan
- sword
- butterfly net
- watering can
- shield
- bubble wand
- fishing pole/crossbow

For playability on the 28th, we hope to have
- the boss with all attacks, health implemented (AI and balance not tuned)
- the main character can move and attack with at least 3 weapons (ideally 6), and they can be upgraded in some way
- necessary ui implemented (health bars, menu screen)
- sound, dialogue structures created and largely implemented, if not fully
- sprite engine fully implemented (as in skeletal animations, not necessarily 2.5d hitbox/sprite disjoint)

We'll look to meet on the 21st at some time to reevaluate progress.