package icbm.gangshao.terminal;

import icbm.gangshao.ISpecialAccess;
import icbm.gangshao.shimian.IScroll;
import java.util.List;

public interface ITerminal extends ISpecialAccess, IScroll {
    List<String> getTerminalOuput();

    boolean addToConsole(final String p0);
}
