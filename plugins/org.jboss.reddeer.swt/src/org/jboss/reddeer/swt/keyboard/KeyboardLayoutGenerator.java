/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.swt.keyboard;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.swt.keyboard.internal.DefaultKeyboard;

/**
 * Utility to generate a sample .keyboard mapping file.
 * Invoke as:
 * <p>KeyboardLayoutGenerator [--alphabetics alphabetics] [--numerics numeric] [--extras extras]
 * 
 */
public class KeyboardLayoutGenerator {

    private static final String ALPHABETICS_DEFAULT = "abcdefghijklmnopqstuvwxyz";
    
    private static final String NUMERICS_DEFAULT = "01234567890";
    
    private static final String EXTRAS_DEFAULT = "&é\"'(-è_çà)=$ù*,;:!€$£";
    
    public static void main(String[] args) {
        String alphabetics = ALPHABETICS_DEFAULT;
        String numerics = NUMERICS_DEFAULT;
        String extras = EXTRAS_DEFAULT;
        for(int i=0; i < args.length;++i) {
            if ("--alphabetics".equalsIgnoreCase(args[i])) {
                alphabetics = args[++i];
            } else if ("--numerics".equalsIgnoreCase(args[i])) {
                numerics = args[++i];
            } else if ("--extras".equalsIgnoreCase(args[i])) {
                extras = args[++i];
            }
        }
        KeyboardLayoutGenerator gen = new KeyboardLayoutGenerator();
        gen.run(alphabetics, numerics, extras);
    }

    public void run(String alphabetics, String numerics, String extras) {
        System.setProperty(DefaultKeyboardLayout.KEYBOARD_LAYOUT, KeyboardLayoutGenerator.class.getPackage() + ".empty");

        Display display = new Display();
        final Keyboard keyboard = new DefaultKeyboard();
        Shell shell = new Shell(display);
        shell.setLayout(new GridLayout(1, false));

        final Text text = new Text(shell, SWT.MULTI | SWT.LEAD | SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        final KeySequenceGenerator generator = createGenerator();
        final String charSequence = new StringBuilder().append(alphabetics).append(numerics).append(extras).toString();
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    generate(charSequence, generator, text, keyboard);
                } catch (Throwable e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } finally {
                    System.exit(0);
                }
            };
        }.start();

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }

    private static void generate(String charSequence, KeySequenceGenerator generator, final Text text, final Keyboard keyboard)
            throws IOException {
        Writer fileWriter = new FileWriter("keyboard.layout");
        for (int i=0; i < charSequence.length();++i) {
            combination(generator, fileWriter, text, charSequence.charAt(i), keyboard);
        }

        fileWriter.close();
    }

    private static KeySequenceGenerator createGenerator() {
        if (isMac()) {
            return new KeySequenceGenerator(SWT.ALT, SWT.SHIFT, SWT.CTRL, SWT.COMMAND);
        } else {
            return new KeySequenceGenerator(SWT.ALT, SWT.SHIFT, SWT.CTRL);
        }
    }

    private static void combination(KeySequenceGenerator generator, final Writer writer, final Text text, char ch,
            final Keyboard keyboard) {
        for(List<Integer> list : generator.getResult()) {
            final List<Integer> combinationList = new ArrayList<>(list);
            combinationList.add((int) ch);

            if (filter(combinationList))
                continue;
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    text.setText("");
                    text.setFocus();
                    keyboard.invokeKeyCombination(toPrimitive(combinationList));
                }
            });
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Display.getDefault().syncExec(new Runnable() {

                @Override
                public void run() {
                    try {
                        String str = text.getText();
                        if (str.length() > 0) {
                            writer.write(text.getText() + " " + toString(combinationList));
                            writer.write('\n');
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                private String toString(List<Integer> combinationList) {
                    StringBuilder builder = new StringBuilder();
                    boolean first = true;
                    for (int i = 0; i < combinationList.size() - 1; ++i) {
                        String str;
                        int val = combinationList.get(i);
                        switch (val) {
                        case SWT.CTRL:
                            str = "ctrl";
                            break;
                        case SWT.ALT:
                            str = "alt";
                            break;
                        case SWT.COMMAND:
                            str = "cmd";
                            break;
                        case SWT.SHIFT:
                            str = "shift";
                            break;
                        default:
                            str = Integer.toString(val);
                            break;
                        }
                        if (!first) {
                            builder.append('+');
                        }
                        builder.append(str);
                        first = false;
                    }
                    if (!first) {
                        builder.append('+');
                    }
                    builder.append((char) (int) combinationList.get(combinationList.size() - 1));
                    return builder.toString();
                }
            });
        }
    }

    private static int[] toPrimitive(List<Integer> combinationList) {
        int[] result = new int[combinationList.size()];

        for (int i = 0; i < combinationList.size(); ++i) {
            result[i] = combinationList.get(i);
        }
        return result;
    }

    private static boolean filter(List<Integer> keys) {

        return // quit
        keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, (int) 'Q' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, (int) 'q' }))
                // logout
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, SWT.ALT, (int) 'Q' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, SWT.ALT, (int) 'q' }))
                // minimize
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, (int) 'm' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, (int) 'M' }))
                // screenshot
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, SWT.SHIFT, (int) '4' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, SWT.CTRL, SWT.SHIFT, (int) '4' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, SWT.SHIFT, (int) '3' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, SWT.CTRL, SWT.SHIFT, (int) '3' }))
                // hide
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, (int) 'h' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, (int) 'H' }))
                // paste
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, (int) 'v' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, (int) 'V' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.CTRL, (int) 'v' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.CTRL, (int) 'V' }))
                // cut
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, (int) 'x' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.COMMAND, (int) 'X' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.CTRL, (int) 'x' }))
                || keys.equals(Arrays.asList(new Integer[] { SWT.CTRL, (int) 'X' }))
        //
        ;
    }

    private static boolean isMac() {
        String swtPlatform = SWT.getPlatform();
        return ("carbon".equals(swtPlatform) || "cocoa".equals(swtPlatform));
    }

    private static class KeySequenceGenerator {

        private List<List<Integer>> result;

        /**
         * @param values
         *            the values that should be combined.
         */
        KeySequenceGenerator(int... values) {
            result = new ArrayList<List<Integer>>();
            for (int i = 1; i <= values.length; i++) {
                FixedSizeKeySequenceGenerator combinationGenerator = new FixedSizeKeySequenceGenerator(i, values);
                result.addAll(combinationGenerator.getResult());
            }
        }
        
        List<List<Integer>> getResult() {
            return result;
        }


        class FixedSizeKeySequenceGenerator {
            private List<List<Integer>> result;

            FixedSizeKeySequenceGenerator(int r, int[] elements) {
                result = computeCombinations(elements, r);
            }

            List<List<Integer>> getResult() {
                return result;
            }

            private void combinationUtil(int arr[], int data[], int start, int end, int index, int r,
                    List<List<Integer>> result) {
                // Current combination is ready to be printed, print it
                if (index == r) {
                    List<Integer> comb = new ArrayList<>();
                    for (int j = 0; j < r; j++) {
                        comb.add(data[j]);
                    }
                    result.add(comb);
                    return;
                }

                // replace index with all possible elements. The condition
                // "end-i+1 >= r-index" makes sure that including one element
                // at index will make a combination with remaining elements
                // at remaining positions
                for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
                    data[index] = arr[i];
                    combinationUtil(arr, data, i + 1, end, index + 1, r, result);
                }
            }

            // The main function that prints all combinations of size r
            // in arr[] of size n. This function mainly uses combinationUtil()
            private List<List<Integer>> computeCombinations(int arr[], int r) {
                int data[] = new int[r];
                List<List<Integer>> result = new ArrayList<>();

                combinationUtil(arr, data, 0, arr.length - 1, 0, r, result);
                return result;
            }
        }

    }
}