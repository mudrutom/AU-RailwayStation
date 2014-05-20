# AU Railway Station

Designing of a control system for a railway station.

## Task

Formalize a control system for given railway station layout using [first-order logic](http://en.wikipedia.org/wiki/First-order_logic)
and prove that it is correct and safe via automatic reasoning tools. A railway station is a connected directed acyclic graph (DAG).

## Railway station properties
* Every node with more than one outgoing edge is a switch.
* Time dependent elements of a railway station are:
 * positions of moving trains
 * states of signalling devices at entrance nodes
 * states of switches
* A train is located at exactly one node in each moment.
* Train movement always follow graph edge orientation (they can not go reverse).
* When there is a train in some node, then it has arrived there before (it was not there from time immemorial) and it shall depart
  later (but it is not clear when – it depends on the "will of an engine driver").
* Signalling devices are located at entrance nodes only. A signalling device can block the entrance node: When the signalling device
  is closed, a train can not enter the railway station and it must wait at the entrance node; when the signalling device is opened,
  the train might (but doesn't have to) enter the railway station. Every train (or its engine driver) always respects signalling devices.
  Every entrance node has exactly one outgoing edge, that is, it is never a switch.
* Every train has assigned an exit it wants to reach. This exit remains fixed during the train movement in the railway station.
* The railway station control system routes the train to its exit by switching switches. The control system can arbitrarily switch any switch.
* When an entrance node is empty, then a new train can appear there at any moment (even right after the entrance of the previous train).

## Railway station critical situations
We recognize the following critical situations in a railway station:
* A train is situated at a switch node when the switch is switched.
* There are two or more trains at the same node at the same time.
* A signalling device never opens (it stays closed all the time).
