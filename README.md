## Circus

Circus is a warehouseLayout order routing service.

## Usage

Running the project opens a command-line interface, which contains an _empty_ warehouseLayout. Use commands to modify the state of the warehouseLayout.

### Commands

- ``create-part <id> <name> <description>``: Creates a part (with the given id, name, and description) and adds it to the part catalogue. If an part with the given id already exists in the warehouseLayout, then no part is created. 
- ``create-storage-unit <type> <x> <y> <capacity>``: Creates a storage unit (of the given type; one of ``rack`` or ``depot``) at the given tile coordinates. If the tile at the given coordinates is non-empty, then no storage unit is created.
- ``display-parts``: Display the part catalogue of the warehouseLayout.
- ``display-warehouseLayout``: Display the warehouseLayout layout.
- ``display-storage-unit-info <x> <y>``: Display info about a storage unit at the given location.
- ``insert-item <part_id>``: Insert an Item (consisting of the Part with the given part_id) into an available rack in the warehouseLayout. If there is no part with the given id, or no rack is available, then no item is inserted.
- ``help <name>``: Provides help information for the command with the given name.

### Example
Note that due to randomness, running the same commands might not result in identical output.
```
> create-part 1 Mango "A delectable fruit"
Created Part{id=1, name='Mango', description='A delectable fruit'} and added it to the part catalogue.
> create-part 2 "Crack Cocaine" "A children's candy"
Created Part{id=2, name='Crack Cocaine', description='A children's candy'} and added it to the part catalogue.
> display-parts
(2) parts in the part catalogue
- Part{id=1, name='Mango', description='A delectable fruit'}
- Part{id=2, name='Crack Cocaine', description='A children's candy'}

> create-storage-unit rack 0 0 1
Created rack at (0, 0)
> create-storage-unit rack 5 0 3
Created rack at (5, 0)
> create-storage-unit receive-depot 5 5 -1
> display-warehouseLayout
======================
EmptyTile        .
ReceiveDepot     R
ShipDepot        S
Rack             X
======================
   0 1 2 3 4 5 6 7 8 9
 0 X . . . . X . . . .
 1 . . . . . . . . . .
 2 . . . . . . . . . .
 3 . . . . . . . . . .
 4 . . . . . . . . . .
 5 . . . . . R . . . .
 6 . . . . . . . . . .
 7 . . . . . . . . . .
 8 . . . . . . . . . .
 9 . . . . . . . . . .
> receive-item 1
Placed item into ReceiveDepot at (5 5)
Order id: 15eccfef-a350-4cb1-9904-8faa461f7985
> check-order 15eccfef-a350-4cb1-9904-8faa461f7985
Status: Pending
```
