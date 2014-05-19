% all the path constants need to be different
fof(pathAllDiff, axiom, (
   ((in2_out2_0 != in2_out1_0) & (in2_out2_0 != in1_out2_0) & (in2_out2_0 != in1_out1_0) & (in2_out1_0 != in1_out2_0) & (in2_out1_0 != in1_out1_0) & (in1_out2_0 != in1_out1_0))
)).

% switch configuration axiom for (in2->out2)#0
fof(conf_in2_out2_0, axiom, (
   (![X]: (conf(X, in2_out2_0) <=> ((switch(X, v2) = out2))))
)).
% free path axiom for (in2->out2)#0
fof(free_in2_out2_0, axiom, (
   (![X]: (free(X, in2_out2_0) <=> (![T]: (~at(X, T, v2) & ~at(X, T, out2) & ~at(X, T, v1)))))
)).

% switch configuration axiom for (in2->out1)#0
fof(conf_in2_out1_0, axiom, (
   (![X]: (conf(X, in2_out1_0) <=> ((switch(X, v2) = out1))))
)).
% free path axiom for (in2->out1)#0
fof(free_in2_out1_0, axiom, (
   (![X]: (free(X, in2_out1_0) <=> (![T]: (~at(X, T, v2) & ~at(X, T, out1) & ~at(X, T, v1)))))
)).

% switch configuration axiom for (in1->out2)#0
fof(conf_in1_out2_0, axiom, (
   (![X]: (conf(X, in1_out2_0) <=> ((switch(X, v1) = out2))))
)).
% free path axiom for (in1->out2)#0
fof(free_in1_out2_0, axiom, (
   (![X]: (free(X, in1_out2_0) <=> (![T]: (~at(X, T, v1) & ~at(X, T, out2) & ~at(X, T, v2)))))
)).

% switch configuration axiom for (in1->out1)#0
fof(conf_in1_out1_0, axiom, (
   (![X]: (conf(X, in1_out1_0) <=> ((switch(X, v1) = out1))))
)).
% free path axiom for (in1->out1)#0
fof(free_in1_out1_0, axiom, (
   (![X]: (free(X, in1_out1_0) <=> (![T]: (~at(X, T, v1) & ~at(X, T, out1) & ~at(X, T, v2)))))
)).

% the path ready axiom for (in1->out1)#0
fof(ready_in1_out1_0, axiom, (
   (![X]: (ready(X, in1_out1_0) <=> ((clock(X) = in1) & free(X, in1_out1_0) & (?[T]: (at(X, T, in1) & (gate(T) = out1))))))
)).
% the path ready axiom for (in1->out2)#0
fof(ready_in1_out2_0, axiom, (
   (![X]: (ready(X, in1_out2_0) <=> ((clock(X) = in1) & free(X, in1_out2_0) & (?[T]: (at(X, T, in1) & (gate(T) = out2))))))
)).

% the path ready axiom for (in2->out1)#0
fof(ready_in2_out1_0, axiom, (
   (![X]: (ready(X, in2_out1_0) <=> ((clock(X) = in2) & free(X, in2_out1_0) & (?[T]: (at(X, T, in2) & (gate(T) = out1))))))
)).
% the path ready axiom for (in2->out2)#0
fof(ready_in2_out2_0, axiom, (
   (![X]: (ready(X, in2_out2_0) <=> ((clock(X) = in2) & free(X, in2_out2_0) & (?[T]: (at(X, T, in2) & (gate(T) = out2))))))
)).

% control the switch configuration for (in1->out1)#0
fof(conf_in1_out1_0, axiom, (
   (![X]: (((ready(X, in1_out1_0)) | (conf(X, in1_out1_0) & ~free(X, in1_out1_0))) => conf(succ(X), in1_out1_0)))
)).
% control the switch configuration for (in1->out2)#0
fof(conf_in1_out2_0, axiom, (
   (![X]: (((ready(X, in1_out2_0)) | (conf(X, in1_out2_0) & ~free(X, in1_out2_0))) => conf(succ(X), in1_out2_0)))
)).

% open the signal in1 when some outgoing path is ready
fof(open_in1, axiom, (
   (![X]: (open(succ(X), in1) <=> (ready(X, in1_out1_0) | ready(X, in1_out2_0))))
)).

% control the switch configuration for (in2->out1)#0
fof(conf_in2_out1_0, axiom, (
   (![X]: (((ready(X, in2_out1_0)) | (conf(X, in2_out1_0) & ~free(X, in2_out1_0))) => conf(succ(X), in2_out1_0)))
)).
% control the switch configuration for (in2->out2)#0
fof(conf_in2_out2_0, axiom, (
   (![X]: (((ready(X, in2_out2_0)) | (conf(X, in2_out2_0) & ~free(X, in2_out2_0))) => conf(succ(X), in2_out2_0)))
)).

% open the signal in2 when some outgoing path is ready
fof(open_in2, axiom, (
   (![X]: (open(succ(X), in2) <=> (ready(X, in2_out1_0) | ready(X, in2_out2_0))))
)).

% the control clock has to be in one of the input nodes
fof(clockOptions, axiom, (
   (![X]: ((clock(X) = in1) | (clock(X) = in2)))
)).
% the sequence of tics of the control clock
fof(clockTic, axiom, (
   (![X]: ((succ(X) != X) => ((clock(succ(X)) = in1) <=> (clock(X) = in2))))
)).
fof(clockTic_0, axiom, (
   (![X]: ((succ(X) != X) => ((clock(succ(X)) = in2) <=> (clock(X) = in1))))
)).
