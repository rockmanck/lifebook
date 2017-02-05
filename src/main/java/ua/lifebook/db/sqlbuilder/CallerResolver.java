package ua.lifebook.db.sqlbuilder;

class CallerResolver {

    /**
     * Gets class from which {@link ua.lifebook.db.sqlbuilder.DynamicSqlBuilder#sql(String)}
     * was called from stack trace
     * @return resulting {@link Class Class<?>} object
     * @throws ClassNotFoundException If the class was not found
     */
    <T> Class<?> getCaller(Class<T> calledClass) throws ClassNotFoundException {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        final String simpleName = calledClass.getName();

        for (int i = 0; i < stackTrace.length - 1; i++) {
            final StackTraceElement traceElement = stackTrace[i];
            if (traceElement.getClassName().equals(simpleName))

                return getClass().getClassLoader().loadClass(findFirstAppropriate(stackTrace, i, simpleName));
        }

        throw new ClassNotFoundException("Can't find " + simpleName + " in stack trace.");
    }

    private String findFirstAppropriate(StackTraceElement[] stackTrace, int current, String calledClass) throws ClassNotFoundException {
        for (int i = current; i < stackTrace.length; i++) {
            final StackTraceElement element = stackTrace[i];
            final String className = element.getClassName();
            if (!className.contains("$$Lambda") && !className.equals(calledClass)) return className;
        }
        throw new ClassNotFoundException("Can't find appropriate class name in stack trace");
    }

}
