Assumptions


Tiles and PathsGame play: 
1. Each tile and tower is 32 x 32 pixels
2. Game is 256 wide x 544 height - this is assuming the board shown in the client requirements
3. One tick is 0.3 seconds.


Building:
1. Tower’s shooting radius: LONG_RADIUS = 5.
2. Campfire’s support radius:  MED_RADIUS = 3.
3. A zombie pit spawns a zombie every 1 round.
4. A vampire castle spawns a vampire every 5 rounds.


Enemy and allied soldier:
1. Health: LOW_HEALTH = 10, MED_HEALTH = 30, HIGH_HEALTH = 100.
2. Damage level: LOW_DAMAGE = 2, MED_DAMAGE = 5, HIGH_DAMAGE = 10
3. Looted exp: LOW_EXP = 1, MED_EXP = 2, HIGH_EXP = 5.
4. Looted gold: LOW_GOLD = 1, MED_GOLD = 2, HIGH_GOLD = 3.
5. Support & attack radius:SHORT_RADIUS = 1, MED_RADIUS= 3, LONG_RADIUS = 5.
6. Vampire has 30% chance deal critical 30 damage, 70% chance deal 10 damage.
7. For a slug, the chance of dropping a basic item is 30% (5% for each type of item).
8. For a slug, the chance of dropping a card is 21% (3 % for each type of card.
9. For a zombie, the chance of dropping a basic item is 60% (10% for each type of item).
10. For a zombie, the chance of dropping a card is 70% (10 % for each type of card.
11. For a vampire, the chance of dropping a basic item is 90% (10% for each type of item).
12. For a vampire, the chance of dropping a card is 100% (10% for a vampire castle card, 15 % for other types of card). 


Character:
1. START_HALTH = 100, START_EXP = 0, START_GOLD = 0, BASE_DAMEGE = 5.
2. Health potion restores character health to 100


Goal:
1. Goal: GOAL_WIN_EXP = 500
2. Goal: GOAL_WIN_GOLD = 250


Items:
1. Gold bags randomly spawn on the path and each contains 1 gold.
2. Gold bag has a chance of 5% to spawn on the path per tick.
3. Health potion has a chance 50% to spawn on the path per tick.
4. At most 1 health potion on the path at any time.
5. Sword Cost: 5 gold
6. Stake Cost: 10 gold
7. Staff Cost: 5 gold
8. Shield Cost: 20 gold
9. Helmet: 10 gold
10. Armour: 5 gold

