package Controller;

import View.Pages.ResizablePanel;

import java.util.Collection;

public class PanelsManager
{
    Collection<ResizablePanel> actualScenario,oldScenario;;

    public PanelsManager(Collection<ResizablePanel> panels){
        oldScenario = this.actualScenario = panels;
    }

    public void changePanel(ResizablePanel first,Collection<ResizablePanel> newScenario)
    {
        oldScenario = actualScenario;
        actualScenario.forEach(p->{p.setVisible(p.equals(first));});
        actualScenario = newScenario;
    }
    public Collection<ResizablePanel> getScenario()  { return oldScenario; }

}
