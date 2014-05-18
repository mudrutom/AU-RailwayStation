% all the node constants need to be different
fof(nodesAllDiff, axiom, (
   ((in1 != v1) & (in1 != in2) & (in1 != v2) & (in1 != out1) & (in1 != out2) & (v1 != in2) & (v1 != v2) & (v1 != out1) & (v1 != out2) & (in2 != v2) & (in2 != out1) & (in2 != out2) & (v2 != out1) & (v2 != out2) & (out1 != out2))
)).

% transition axiom for input node in1
fof(node_in1, axiom, (
   (![X]: ![T]: (at(succ(X), T, in1) <=> (enter(X, T, in1) | (at(X, T, in1) & (~goes(X, in1) | ~open(X, in1))))))
)).
% entrance axiom for input node in1
fof(entrance_in1, axiom, (
   (![X]: ![T1]: ![T2]: (((T1 != T2) & at(X, T1, in1)) => ~at(X, T2, in1)))
)).
% transition axiom for inner node v1
fof(node_v1, axiom, (
   (![X]: ![T]: (at(succ(X), T, v1) <=> ((at(X, T, v1) & ~goes(X, v1)) | (at(X, T, in1) & goes(X, in1) & open(X, in1)))))
)).
% transition axiom for input node in2
fof(node_in2, axiom, (
   (![X]: ![T]: (at(succ(X), T, in2) <=> (enter(X, T, in2) | (at(X, T, in2) & (~goes(X, in2) | ~open(X, in2))))))
)).
% entrance axiom for input node in2
fof(entrance_in2, axiom, (
   (![X]: ![T1]: ![T2]: (((T1 != T2) & at(X, T1, in2)) => ~at(X, T2, in2)))
)).
% transition axiom for inner node v2
fof(node_v2, axiom, (
   (![X]: ![T]: (at(succ(X), T, v2) <=> ((at(X, T, v2) & ~goes(X, v2)) | (at(X, T, in2) & goes(X, in2) & open(X, in2)))))
)).
% transition axiom for output node out1
fof(node_out1, axiom, (
   (![X]: ![T]: (at(succ(X), T, out1) <=> ((at(X, T, v1) & goes(X, v1) & (switch(X, v1) = out1)) | (at(X, T, v2) & goes(X, v2) & (switch(X, v2) = out1)))))
)).
% transition axiom for output node out2
fof(node_out2, axiom, (
   (![X]: ![T]: (at(succ(X), T, out2) <=> ((at(X, T, v1) & goes(X, v1) & (switch(X, v1) = out2)) | (at(X, T, v2) & goes(X, v2) & (switch(X, v2) = out2)))))
)).

% no train can be at any two nodes in the same time
fof(singularTrainLocation, axiom, (
   (![X]: ![T]: ![N1]: ![N2]: ((at(X, T, N1) & at(X, T, N2)) => (N1 = N2)))
)).
% the train driver has to go eventually
fof(trainDriverGoes, axiom, (
   (![X]: ![T]: ![N]: (at(X, T, N) => (?[Y]: (((X = Y) | less(X, Y)) & goes(Y, N)))))
)).
