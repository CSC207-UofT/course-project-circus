## Circus

Circus is a warehouse order routing service.

## Usage

Runinng the project opens a command-line interface, which contains an _empty_ warehouse. Use commands to modify the state of the warehouse.

### Commands

- ``create-item <id> <name> <description>``: Creates an item (with the given id, name, and description) and adds it to the warehouse inventory catalogue. If an item with the given id already exists in the warehouse, then no item is created. 
- ``create-storage-unit <type> <x> <y> <capacity>``: Creates a storage unit (of the given type; one of ``rack`` or ``depot``) at the given tile coordinates. If the tile at the given coordinates is non-empty, then no storage unit is created.
- ``display-inventory``: Display the inventory catalogue of the warehouse.
- ``display-warehouse``: Display the warehouse layout.
- ``display-storage-unit-info <x> <y>``: Display info about a storage unit at the given location.
- ``insert-item <id>``: Insert the item with the given id into an available rack in the warehouse. If there is no item with the given id, or no rack is available, then no item is inserted.
- ``help <name>``: Provides help information for the command with the given name.

### Example
```
> create-item 1 Mango "A delectable fruit"
Created Item{id=1, name='Mango', description='A delectable fruit'} and added it to the inventory catalogue.
> create-item 2 "Crack Cocaine" "A children's candy"
Created Item{id=2, name='Crack Cocaine', description='A children's candy'} and added it to the inventory catalogue.
> display-inventory
(2) items in the inventory catalogue
- Item{id=1, name='Mango', description='A delectable fruit'}
- Item{id=2, name='Crack Cocaine', description='A children's candy'}

> create-storage-unit rack 0 0 1
Created rack at (0, 0)
> create-storage-unit rack 5 0 3
Created rack at (5, 0)
> display-warehouse
   0 1 2 3 4 5 6 7 8 9
 0 R X X X X R X X X X
 1 X X X X X X X X X X
 2 X X X X X X X X X X
 3 X X X X X X X X X X
 4 X X X X X X X X X X
 5 X X X X X X X X X X
 6 X X X X X X X X X X
 7 X X X X X X X X X X
 8 X X X X X X X X X X
 9 X X X X X X X X X X
 > insert-item 1
 Great Success! Inserted item into Rack at (0, 0) 
 > insert-item 1
 Great Success! Inserted item into Rack at (0, 5)
 > display-storage-unit-info 0 5
Rack{capacity=3, currentItem=Item{id=1, name="Mango", description="A delectable fruit"}, size=1}
